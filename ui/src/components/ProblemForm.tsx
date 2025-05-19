"use client";

import { useState } from "react";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import Editor from "@/components/Editor";
import Icons from "@/lib/icons";
import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { type FormSchema, formSchema } from "@/lib/form/problem";
import { toast } from "sonner";

function getDefaultValues(initialData: any): FormSchema {
  return {
    title: initialData?.title || "",
    prompt: initialData?.prompt || "",
    difficulty: initialData?.difficulty || "Easy",
    sampleInputs: initialData?.sampleInputs || "",
    sampleOutputs: initialData?.sampleOutputs || "",
    hiddenInputs: initialData?.hiddenInputs || "",
    hiddenOutputs: initialData?.hiddenOutputs || "",
    javaTesterCode:
      initialData?.testerCodeList.find((tc: any) => tc.lang === "java").code ||
      "",
    pythonTesterCode:
      initialData?.testerCodeList.find((tc: any) => tc.lang === "python")
        .code || "",
    javaTemplateCode:
      initialData?.templateCodeList.find((tc: any) => tc.lang === "java")
        .code || "",
    pythonTemplateCode:
      initialData?.templateCodeList.find((tc: any) => tc.lang === "python")
        .code || "",
  };
}

export default function ProblemForm({
  initialData,
  submitAction,
}: {
  initialData?: any;
  submitAction: (problemBody: any) => any;
}) {
  const router = useRouter();
  const [loading, setLoading] = useState(false);

  const form = useForm<FormSchema>({
    resolver: zodResolver(formSchema),
    defaultValues: getDefaultValues(initialData),
  });

  async function onSubmit(values: FormSchema) {
    setLoading(true);
    await submitAction({
      title: values.title,
      prompt: values.prompt,
      sampleInputs: values.sampleInputs,
      sampleOutputs: values.sampleOutputs,
      hiddenInputs: values.hiddenInputs,
      hiddenOutputs: values.hiddenOutputs,
      difficulty: values.difficulty,
      testerCodeList: [
        {
          lang: "java",
          code: values.javaTesterCode,
        },
        {
          lang: "python",
          code: values.pythonTesterCode,
        },
      ],
      templateCodeList: [
        {
          lang: "java",
          code: values.javaTemplateCode,
        },
        {
          lang: "python",
          code: values.pythonTemplateCode,
        },
      ],
    });
    setLoading(false);
    toast.success("Problem saved successfully");
    router.push("/");
  }

  return (
    <Form {...form}>
      <form
        className="flex flex-col gap-8"
        onSubmit={form.handleSubmit(onSubmit)}
      >
        <div className="flex flex-col gap-4">
          <h3 className="scroll-m-20 text-2xl font-semibold tracking-tight">
            1. Basic Information
          </h3>
          <hr />
          <FormField
            control={form.control}
            name="title"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Title</FormLabel>
                <FormControl>
                  <Input placeholder="e.g. Add to Numbers" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="prompt"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Prompt</FormLabel>
                <FormControl>
                  <Textarea
                    rows={10}
                    placeholder="e.g. Write a function to add 2 numbers"
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="difficulty"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Difficulty</FormLabel>
                <Select
                  onValueChange={field.onChange}
                  defaultValue={field.value}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="Easy">Easy</SelectItem>
                    <SelectItem value="Medium">Medium</SelectItem>
                    <SelectItem value="Hard">Hard</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage />
              </FormItem>
            )}
          />
        </div>
        <div className="flex flex-col gap-4">
          <h3 className="scroll-m-20 text-2xl font-semibold tracking-tight">
            2. Input and Output
          </h3>
          <hr />
          <FormField
            control={form.control}
            name="sampleInputs"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Sample Inputs</FormLabel>
                <FormControl>
                  <Textarea rows={10} {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="sampleOutputs"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Sample Outputs</FormLabel>
                <FormControl>
                  <Textarea rows={10} {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="hiddenInputs"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Hidden Inputs</FormLabel>
                <FormControl>
                  <Textarea rows={10} {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="hiddenOutputs"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Hidden Outputs</FormLabel>
                <FormControl>
                  <Textarea rows={10} {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
        </div>
        <div className="flex flex-col gap-4">
          <h3 className="scroll-m-20 text-2xl font-semibold tracking-tight">
            3. Tester Code
          </h3>
          <hr />
          <FormField
            control={form.control}
            name="javaTesterCode"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Java Tester Code</FormLabel>
                <FormControl>
                  <div className="h-96">
                    <Editor language="java" {...field} />
                  </div>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="pythonTesterCode"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Python Tester Code</FormLabel>
                <FormControl>
                  <div className="h-96">
                    <Editor language="python" {...field} />
                  </div>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
        </div>
        <div className="flex flex-col gap-4">
          <h3 className="scroll-m-20 text-2xl font-semibold tracking-tight">
            4. Template Code
          </h3>
          <hr />
          <FormField
            control={form.control}
            name="javaTemplateCode"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Java Template Code</FormLabel>
                <FormControl>
                  <div className="h-96">
                    <Editor language="java" {...field} />
                  </div>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="pythonTemplateCode"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Python Template Code</FormLabel>
                <FormControl>
                  <div className="h-96">
                    <Editor language="python" {...field} />
                  </div>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
        </div>
        <Button
          className="w-fit inline-flex gap-2"
          type="submit"
          disabled={loading}
        >
          {loading && <Icons.loader className="h-4 w-4 animate-spin" />}
          Submit
        </Button>
      </form>
    </Form>
  );
}
