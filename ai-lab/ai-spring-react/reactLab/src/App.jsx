import Header from "./components/include/Header";
import Footer from "./components/include/Footer";
import AppRouter from "./router/AppRouter";
import { useEffect } from "react";
import { useNavigate } from "react-router";
import TokenExpirationCheck2 from "./components/auth/TokenExpirationCheck2";

//App.jsx에 있던 라우트 설정을 router폴더를 추가로 작성한 후 AppRouter.jsx로 모두 이관하였다.
const App = () => {
  const navigate = useNavigate()
  //로그인이 성공하면 localStorage 저장됨.
  const token = localStorage.getItem("accessToken")
  //토큰이 만료되었는지 여부 체크 - 60초전에 토큰 연장처리
  //토큰이 만료되었으면 로그인이 풀린다.
  //TokenExpirationChec2함수이름임
  const isTokenExpired = TokenExpirationCheck2(token)
  useEffect(() => {
    if(isTokenExpired){
      alert("토큰이 만료되어 로그아웃 됩니다.");
      //토큰이 만료되면 /home으로 이동하기
      navigate("/home")
      //로그인 할 때 저장해 둔 값 모두 삭제하기
      localStorage.removeItem("accessToken")
      localStorage.removeItem("refreshToken")
      localStorage.removeItem("check")
      localStorage.removeItem("username")
      localStorage.removeItem("email")
      localStorage.removeItem("role")
      localStorage.removeItem("id")
    }
  },[isTokenExpired]) //의존성 배열에 값이 변하면 useEffect도 다시 호출된다.
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