import ProblemForm from "@/components/ProblemForm";
import { putProblemAction } from "@/lib/actions/problem";
import { getProblem } from "@/lib/api/problem";

export default async function EditProblemPage({
  params,
}: {
  params: { pid: string };
}) {
  const problem = await getProblem(params.pid);

  async function editProblemAction(problemBody: any) {
    "use server";
    return putProblemAction(params.pid, problemBody);
  }

  return (
    <main className="max-w-3xl mx-auto px-4 flex flex-col gap-8">
      <h1 className="scroll-m-20 text-4xl font-extrabold tracking-tight lg:text-5xl">
        Add a Problem
      </h1>
      <ProblemForm initialData={problem} submitAction={editProblemAction} />
    </main>
  );
}
