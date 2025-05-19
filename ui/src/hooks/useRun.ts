import { getRun } from "@/lib/api/run";
import queryClient from "@/lib/queryClient";
import { useQuery } from "@tanstack/react-query";

export default function useRun(rid?: string) {
  return useQuery({
    queryKey: ["run", rid],
    queryFn: async () => {
      if (!rid) return null;
      const run = await getRun(rid);
      if (run.type === "Submission" && run.status !== "Queued") {
        queryClient.invalidateQueries({
          queryKey: ["problem", run.problem.id, "submissions"],
        });
      }
      return run;
    },
    enabled: !!rid,
  });
}
