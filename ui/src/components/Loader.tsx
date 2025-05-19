import Icons from "@/lib/icons";

export default function Loader() {
  return (
    <div className="py-16 flex items-center justify-center">
      <Icons.loader className="h-12 w-12 animate-spin" />
    </div>
  );
}
