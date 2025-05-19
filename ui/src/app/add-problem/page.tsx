import ProblemForm from "@/components/ProblemForm";
import { postProblemAction } from "@/lib/actions/problem";

export default function AddProblemPage() {
  return (
    <main className="max-w-3xl mx-auto px-4 flex flex-col gap-8">
      <h1 className="scroll-m-20 text-4xl font-extrabold tracking-tight lg:text-5xl">
        Add a Problem
      </h1>
      <ProblemForm submitAction={postProblemAction} />
    </main>
  );
}
