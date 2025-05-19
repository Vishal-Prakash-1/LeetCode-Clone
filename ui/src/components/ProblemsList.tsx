import { cn } from "@/lib/utils";
import Link from "next/link";
import { Button } from "./ui/button";
import Icons from "@/lib/icons";
import ProblemDeleteButton from "./ProblemDeleteButton";

export default async function ProblemsList({ problems }: { problems: any[] }) {
  return (
    <div className="flex flex-col gap-4">
      {problems.map((problem: any) => (
        <div
          key={problem.id}
          className="flex items-center justify-between p-4 border border-neutral-200 rounded-lg"
        >
          <div className="flex items-center gap-2">
            <p
              className={cn("w-fit px-3 py-1 rounded-lg text-white", {
                "bg-green-600": problem.difficulty === "Easy",
                "bg-orange-600": problem.difficulty === "Medium",
                "bg-red-600": problem.difficulty === "Hard",
              })}
            >
              {problem.difficulty}
            </p>
            <p>{problem.title}</p>
          </div>
          <div className="flex gap-2">
            <Button asChild>
              <Link href={`/solve/${problem.id}`}>Solve</Link>
            </Button>
            <Button variant="outline" size="icon" asChild>
              <Link href={`/edit-problem/${problem.id}`}>
                <Icons.edit className="h-4 w-4" />
              </Link>
            </Button>
            <ProblemDeleteButton problem={problem} />
          </div>
        </div>
      ))}
    </div>
  );
}
