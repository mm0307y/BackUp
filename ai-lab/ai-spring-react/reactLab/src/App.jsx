import Header from "./components/include/Header";
import Footer from "./components/include/Footer";
import AppRouter from "./router/AppRouter";
//App.jsx에 있던 라우트 설정을 router폴더를 추가로 작성한 후 AppRouter.jsx로 모두 이관하였다.
const App = () => {
  return (
    <>
      <Header />
      <div>
        <AppRouter />
      </div>
      <Footer />
    </>
  );
}

export default App;
/*
  - http://localhost:3000/index.html
  - <div id="root"></div>
  - root에 대한 정보는 index.js에서 참조 한다.
  document.querySelector("#root")
  - index.js에서 App import한다
  - App.jsx의 return에 있는 태그가 화면 출력된다.
  - 그런데 이번에는 App.jsx에 메뉴를 클릭했을 때 보여줄 페이지에
  대한 링크를 걸어 준다.
*/