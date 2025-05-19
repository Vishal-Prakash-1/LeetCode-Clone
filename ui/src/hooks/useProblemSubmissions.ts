import { getProblemSubmissions } from "@/lib/api/problem";
import { useQuery } from "@tanstack/react-query";

export default function useProblemSubmissions(pid: string) {
  return useQuery({
    queryKey: ["problem", pid, "submissions"],
    queryFn: async () => {
      const submissions = await getProblemSubmissions(pid);
      return submissions;
    },
  });
}
