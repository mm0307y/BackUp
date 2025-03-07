import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router'
import { signinTokenLogic } from '../../services/common/SignAuth'
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
      signinTokenLogic(tempUser)
      .then(response => {
        localStorage.clear()
        localStorage.setItem("accessToken", response.accessToken)
        localStorage.setItem("refreshToken", response.refreshToken)
        localStorage.setItem("check", "Y")
        localStorage.setItem("role", response.role)
        localStorage.setItem("email", response.email)
        localStorage.setItem("username", response.username)
        localStorage.setItem("id", response.id)
        navigate("/home")
      })
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