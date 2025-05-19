package execution;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.WaitContainerResultCallback;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.StreamType;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import config.Constants;

public class ContainerService {
    private final DockerClient dockerClient;

    public ContainerService() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .build();
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .build();
        dockerClient = DockerClientImpl.getInstance(config, httpClient);
    }

    public String startContainer(String mountDir, String lang) throws ContainerServiceException {
        try {
            HostConfig hostConfig = new HostConfig()
                    .withBinds(Bind.parse(mountDir + ":" + Constants.CONTAINER_APP_DIR))
                    .withMemory(Constants.CONTAINER_MEMORY);
            String imageName = getContainerImage(lang);
            CreateContainerResponse container = dockerClient.createContainerCmd(imageName)
                    .withHostConfig(hostConfig)
                    .exec();

            dockerClient.startContainerCmd(container.getId()).exec();
            return container.getId();
        } catch (Exception ex) {
            throw new ContainerServiceException("Failed to start container", ex);
        }
    }

    private String getContainerImage(String lang) throws ContainerServiceException {
        return switch (lang) {
            case "java" -> "java-executor";
            case "python" -> "python-executor";
            default -> throw new ContainerServiceException("Unsupported lang");
        };
    }

    public void waitForContainer(String containerId) throws ContainerServiceException {
        try {
            dockerClient.waitContainerCmd(containerId)
                    .exec(new WaitContainerResultCallback())
                    .awaitCompletion();
        } catch (InterruptedException ex) {
            throw new ContainerServiceException("Failed to wait for container to finish", ex);
        }
    }

    public long getExitCode(String containerId) throws ContainerServiceException {
        try {
            InspectContainerResponse containerInfo = dockerClient.inspectContainerCmd(containerId).exec();
            Long exitCode = containerInfo.getState().getExitCodeLong();
            try {
                return exitCode.longValue();
            } catch (NullPointerException ex) {
                throw new ContainerServiceException("Failed to get container exit code", ex);
            }
        } catch (ContainerServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ContainerServiceException("Failed to get container exit code", ex);
        }
    }

    public String[] getContainerLogs(String containerId) throws ContainerServiceException {
        try {
            StringBuilder stdOutBuilder = new StringBuilder();
            StringBuilder stdErrBuilder = new StringBuilder();

            dockerClient.logContainerCmd(containerId)
                    .withStdOut(true)
                    .withStdErr(true)
                    .exec(new ResultCallback.Adapter<Frame>() {
                        @Override
                        public void onNext(Frame frame) {
                            StreamType type = frame.getStreamType();
                            if (type == StreamType.STDOUT) {
                                stdOutBuilder.append(new String(frame.getPayload()));
                            } else if (type == StreamType.STDERR) {
                                stdErrBuilder.append(new String(frame.getPayload()));
                            }
                        }
                    })
                    .awaitCompletion();

            return new String[]{stdOutBuilder.toString(), stdErrBuilder.toString()};
        } catch (Exception ex) {
            throw new ContainerServiceException("Failed to get container logs", ex);
        }
    }

    public void removeContainer(String containerId) throws ContainerServiceException {
        try {
            dockerClient.removeContainerCmd(containerId).exec();
        } catch (Exception ex) {
            throw new ContainerServiceException("Failed to remove container", ex);
        }
    }
}
