import { getProblems } from "@/lib/api/problem";
import ProblemsList from "@/components/ProblemsList";
import { Button } from "@/components/ui/button";
import Link from "next/link";
import Icons from "@/lib/icons";

export default async function HomePage() {
  const problems = await getProblems();

  return (
    <main className="max-w-3xl mx-auto px-4">
      <h1 className="scroll-m-20 text-4xl font-extrabold tracking-tight lg:text-5xl mb-8">
        Problems
      </h1>
      <div className="flex justify-end mb-4">
        <Button asChild>
          <Link href="/add-problem" className="inline-flex gap-2">
            <Icons.add className="h-4 w-4" />
            Add Problem
          </Link>
        </Button>
      </div>
      <ProblemsList problems={problems} />
    </main>
  );
}
