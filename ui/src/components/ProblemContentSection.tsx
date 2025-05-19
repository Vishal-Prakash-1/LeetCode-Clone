import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import ProblemDescription from "./ProblemDescription";
import ProblemSubmissions from "./ProblemSubmissions";
import { ScrollArea } from "./ui/scroll-area";

export default function ProblemContentSection({ problem }: { problem: any }) {
  return (
    <ScrollArea className="h-full">
      <Tabs defaultValue="description">
        <TabsList>
          <TabsTrigger value="description">Description</TabsTrigger>
          <TabsTrigger value="submissions">Submissions</TabsTrigger>
        </TabsList>
        <TabsContent className="pt-2" value="description">
          <ProblemDescription problem={problem} />
        </TabsContent>
        <TabsContent className="pt-2" value="submissions">
          <ProblemSubmissions problem={problem} />
        </TabsContent>
      </Tabs>
    </ScrollArea>
  );
}
