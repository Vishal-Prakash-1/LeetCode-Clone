"use client";

import { useEffect } from "react";
import Loader from "./Loader";
import useRun from "@/hooks/useRun";

export default function RunContent({ rid }: { rid: string | undefined }) {
  const { data: run, isLoading, refetch } = useRun(rid);

  useEffect(() => {
    if (!run) return;

    const intervalId = setInterval(() => {
      if (run.status === "Queued") {
        refetch();
      }
    }, 1000);

    const timoutId = setTimeout(() => clearInterval(intervalId), 30_000);

    return () => {
      clearTimeout(timoutId);
      clearInterval(intervalId);
    };
  }, [run, refetch]);

  return (
    <div className="flex flex-col gap-4">
      {!rid && (
        <p>You must first run or submit your code to view run information.</p>
      )}
      {rid && isLoading && <Loader />}
      {rid && !isLoading && (
        <>
          <div className="flex flex-col gap-2">
            <p className="font-semibold">Status</p>
            <p className="p-4 bg-muted rounded-lg">{run.status}</p>
          </div>
          <div className="flex flex-col gap-2">
            <p className="font-semibold">Verdict</p>
            <p className="p-4 bg-muted rounded-lg">{run.verdict || "None"}</p>
          </div>
          <div className="flex flex-col gap-2">
            <p className="font-semibold">Standard Output</p>
            <pre className="p-4 bg-muted rounded-lg">
              {run.stdOut || "None"}
            </pre>
          </div>
          <div className="flex flex-col gap-2">
            <p className="font-semibold">Standard Error</p>
            <pre className="p-4 bg-muted rounded-lg overflow-auto">
              {run.stdErr || "None"}
            </pre>
          </div>
        </>
      )}
    </div>
  );
}
