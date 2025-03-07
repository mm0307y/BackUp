import React, { useState } from 'react'
import { useNavigate } from 'react-router'
import { oracleJoin } from '../../services/authLogic'
import { signUp } from '../../services/logic/userCreate'

const JoinPage = () => {
    const navigate = useNavigate()
    const [tempUser, setTempUser] = useState({
        username:'', //emp_no
        password:'',
        email:'',
        role:'USER'
    }) 
    //사용자가 이름과 비번과 이메일을 입력할 때 마다 상태값 변화를 체크해서
    //훅에 저장하기
    //반복되는 코드를 줄이기 위해서 각 콤퍼넌트의 id값을 키값으로 사용하여 값을 훅에 저장함.
    const changeUser = (event) => {
        console.log(event.currentTarget.id)
        const id = event.currentTarget.id 
        const value = event.target.value
        console.log(id + value)
        setTempUser({...tempUser, [id]: value}) 
    }
    //button의 경우 디폴트가 submit이어서 이벤트 버블링 이슈
    const join = async(e) => {
        e.preventDefault()
        console.log(tempUser)
        try {
            signUp(tempUser)
            .then(response => {
                alert("회원가입이 완료되었습니다.")
                //회원가입이 성공하면 로그인 페이지로 이동하기
                navigate("/")
            })
        } catch (error) {
            console.error(error +"회원 가입 오류 입니다.")
        }
    }//end of join
    return (
        <>
        <h2>회원가입</h2>
        <hr />
        <form>
            <div>
                <label>Username:</label>
                <input type="text" id="username" onChange={changeUser} />
            </div>
            <div>
                <label>Password:</label>
                <input type="password" id="password" onChange={changeUser} />
            </div>
            <div>
                <label>Email:</label>
                <input type="email" id="email" onChange={changeUser} />
            </div>
            <div>
                    <label>Role:</label>
                    <select id="role" onChange={changeUser} value={tempUser.role}>
                        <option value="USER">User</option>
                        <option value="ADMIN">Admin</option>
                    </select>
            </div>
            <button onClick={join}>회원가입</button>
        </form>
        </>
    )
}

export default JoinPage