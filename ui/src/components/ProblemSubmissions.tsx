"use client";

import SubmissionContent from "./SubmissionContent";
import useProblemSubmissions from "@/hooks/useProblemSubmissions";
import Loader from "./Loader";

export default function ProblemSubmissions({ problem }: { problem: any }) {
  const { data: submissions, isLoading } = useProblemSubmissions(problem.id);

  if (isLoading) {
    return <Loader />;
  }

  return (
    <div className="flex flex-col gap-4">
      {submissions.map((sub: any) => (
        <SubmissionContent key={sub.id} submission={sub} />
      ))}
    </div>
  );
}
