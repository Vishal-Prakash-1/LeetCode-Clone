import { postTestSubmission } from "@/lib/api/submission";
import { useMutation } from "@tanstack/react-query";

export default function useTestSubmissionMutation(
  pid: string,
  lang: string,
  code?: string
) {
  return useMutation({
    mutationFn: async () => {
      const run = await postTestSubmission(pid, lang, code);
      return run;
    },
  });
}
