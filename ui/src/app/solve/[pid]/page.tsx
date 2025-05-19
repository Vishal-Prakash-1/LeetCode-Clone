import ProblemCodeSection from "@/components/ProblemCodeSection";
import ProblemContentSection from "@/components/ProblemContentSection";
import { getProblem } from "@/lib/api/problem";
import {
  ResizableHandle,
  ResizablePanel,
  ResizablePanelGroup,
} from "@/components/ui/resizable";

export default async function ProblemPage({
  params,
}: {
  params: { pid: string };
}) {
  const problem = await getProblem(params.pid);

  return (
    <ResizablePanelGroup className="gap-2" direction="horizontal">
      <ResizablePanel defaultSize={35}>
        <ProblemContentSection problem={problem} />
      </ResizablePanel>
      <ResizableHandle withHandle />
      <ResizablePanel defaultSize={65}>
        <ProblemCodeSection problem={problem} />
      </ResizablePanel>
    </ResizablePanelGroup>
  );
}
