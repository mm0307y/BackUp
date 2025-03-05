import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router'
import { oracleLogin } from '../../services/authLogic'
//rafce단축명령어
const LoginPage = () => {
  const navigate = useNavigate()
  //사용자가 입력한 username 과 password를 넘길 때
  //현재 초기값은 빈 문자열로 되어 있음. -> 사용자가 username과 password를 입력할 때
  //마다 이벤트가 인터셉트 하여 변경사항을 useState에 재정의 해야 함.
  //자바스크립트에서 좌중괄호와 우중괄호는 객체다. -> @RequestParam 처리할지, @RequestBody처리할지
  const [tempUser, setTempUser] = useState({
    username:'',
    password:''
  })
  //사용자가 input 콤포넌트에 값을 입력할 때 마다 (onChange발동하면 감지해서 인터셉트)
  const changeUser = (e) => {
    const id = e.currentTarget.id //화면에 정의된 id를 동적으로 가져오기
    //사용자가 입력한 값 가져오기
    const value = e.target.value //이벤트가 감지된 콤퍼넌트값을 가져오기
    //저 위에 선언한 훅에 값을 재정의 하기
    console.log(id) //username 혹은 password
    //사용자가 입력한 최종값을 훅에 담아둔다.
    setTempUser({...tempUser, [id]: value})
  }//end of changeUser
  //리액트에서 폼 전송을 하는 것은 html에서 하는 것과 분리해서 정리해 둘 것.

  const loginE = async(e) => {
    e.preventDefault() //button사용할 땐 반드시 이벤트 전이 막기
    console.log(tempUser) //사용자가 입력한 값이 들어있나??? @RequestBody 왜냐면 POST
    try {
      //왜 JWT인가? 3000번 도메인과 8000번 도메인 사이에 캐쉬메모리를 공유할 수 없다.
      //스프링은 캐쉬메모리에 정보를 담음.- 그럼 3000번은 접근이 불가하다. - 공유불가
      //로그인 여부를 3000번과 8000번이 동기화할 수 없는 상태이다.
      //이문제는 토큰을 사용하면 해결이 된다. - 실시간이니까
      //로그인 처리할 때 accessToken과 refreshToken을 둘 다 받는다.
      //accessToken은 로그인을 할 때 사용함. refreshToken 서비스를 활용할 때 사용함.
      //왜 accessToken과 refreshToken  발급하나? - 토큰을 연장하기
      //토큰 발행시간 - 로그인 유지 시간- 토큰이 종료되는 60초 전에 너 연장할거야?
      const response  = await oracleLogin(tempUser)
      localStorage.clear()
      localStorage.setItem("accessToken", response.accessToken)
      localStorage.setItem("refreshToken", response.refreshToken)
      localStorage.setItem("check", "Y")
      localStorage.setItem("role", response.role)
      localStorage.setItem("email", response.email)
      localStorage.setItem("username", response.username)
      localStorage.setItem("id", response.id)
      //단 수정된 내용이 반영이 안됨. -> useState훅을 활용해서 화면이 갱신되도록 할 것.
      //window.location.reload() - 화면이 리플래쉬 -서버에 다시 요청이 들어간다.
      navigate("/home") //절대로 a태그는 사용하지 말자.  <Link>사용하세요.
    } catch (error) {
      console.error(error +" : 로그인 에러 입니다.")
    }
  }
  return (
    <>
      <h2>Login</h2>
      <hr />
      <form>
          <div>
              <label>Username:</label>
              <input type="text" id="username" onChange={(e)=>changeUser(e)} />
          </div>
          <div>
              <label>Password:</label>
              <input type="password" id="password" onChange={(e)=>changeUser(e)} />
          </div>
          {/* input type=button 과 button태그를 사용하는 것은 다르다. */}
          <button onClick={loginE}>Login</button>
      </form>
      <Link to="/joinForm">회원가입</Link>       
    </>
  )
}

export default LoginPage