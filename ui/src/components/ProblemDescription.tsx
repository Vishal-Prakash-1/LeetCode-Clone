import { cn } from "@/lib/utils";

export default function ProblemDescription({ problem }: { problem: any }) {
  return (
    <div className="flex flex-col gap-4">
      <h1 className="scroll-m-20 text-2xl font-semibold tracking-tight">
        {problem.title}
      </h1>
      <p
        className={cn("w-fit px-3 py-1 rounded-lg text-white", {
          "bg-green-600": problem.difficulty === "Easy",
          "bg-orange-600": problem.difficulty === "Medium",
          "bg-red-600": problem.difficulty === "Hard",
        })}
      >
        {problem.difficulty}
      </p>
      <p>{problem.prompt}</p>
    </div>
  );
}
