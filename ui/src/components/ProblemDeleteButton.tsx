"use client";

import { useState } from "react";
import Icons from "@/lib/icons";
import { Button } from "./ui/button";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import { deleteProblemAction } from "@/lib/actions/problem";
import { toast } from "sonner";

export default function ProblemDeleteButton({ problem }: { problem: any }) {
  const [loading, setLoading] = useState(false);
  const [show, setShow] = useState(false);

  async function deleteHandler() {
    setLoading(true);
    await deleteProblemAction(problem.id);
    setLoading(false);
    setShow(false);
    toast.success("Problem deleted successfully");
  }

  return (
    <>
      <Button variant="outline" size="icon" onClick={() => setShow(true)}>
        <Icons.delete className="h-4 w-4" />
      </Button>
      <AlertDialog open={show}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Are you sure?</AlertDialogTitle>
            <AlertDialogDescription>
              This action cannot be undone. This will permanently delete the
              problem along with all runs and submissions.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel onClick={() => setShow(false)}>
              Cancel
            </AlertDialogCancel>
            <AlertDialogAction asChild>
              <Button
                className="inline-flex gap-2"
                onClick={deleteHandler}
                disabled={loading}
              >
                {loading && <Icons.loader className="h-4 w-4 animate-spin" />}
                Delete
              </Button>
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
}
