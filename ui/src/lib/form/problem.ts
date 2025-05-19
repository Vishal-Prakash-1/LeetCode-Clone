import { z } from "zod";

export const formSchema = z.object({
  title: z.string().min(3, {
    message: "Title is required and must be atleast 3 characters",
  }),
  prompt: z.string().min(20, {
    message: "Prompt is required and must be atleast 20 characters",
  }),
  sampleInputs: z.string().min(1, {
    message: "Sample inputs are required",
  }),
  sampleOutputs: z.string().min(1, {
    message: "Sample outputs are required",
  }),
  hiddenInputs: z.string().min(1, {
    message: "Hidden inputs are required",
  }),
  hiddenOutputs: z.string().min(1, {
    message: "Hidden outputs are required",
  }),
  difficulty: z.enum(["Easy", "Medium", "Hard"], {
    message: "Diffculty should be one of 'Easy', 'Medium' or 'Hard'",
  }),
  javaTesterCode: z.string().min(1, {
    message: "Java tester code is required",
  }),
  pythonTesterCode: z.string().min(1, {
    message: "Python tester code is required",
  }),
  javaTemplateCode: z.string().min(1, {
    message: "Java template code is required",
  }),
  pythonTemplateCode: z.string().min(1, {
    message: "Python template code is required",
  }),
});

export type FormSchema = z.infer<typeof formSchema>;
