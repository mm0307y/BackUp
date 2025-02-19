import { createBrowserRouter } from "react-router-dom";

const root = createBrowserRouter([
  {
    path: "",
    element: <div className={"bg-red-600"}>Loading...</div>,
  },
  {
    path: "/about",
    element: <div className={"bg-red-600"}>Loading...</div>,
  },
]);
export default root;
