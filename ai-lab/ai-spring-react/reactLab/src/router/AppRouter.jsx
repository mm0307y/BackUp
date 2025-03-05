import React from 'react'
import { Route, Routes } from 'react-router'
import HomePage from '../components/pages/HomePage'
import LoginPage from '../components/auth/LoginPage'
import TentPage from '../components/pages/TentPage'
import SchedulePage from '../components/pages/SchedulePage'
import NoticePage from '../components/pages/NoticePage'
import JoinPage from '../components/auth/JoinPage'
import PrivateRouter from './PrivateRouter'

const AppRouter = () => {
    const role = localStorage.getItem("role") //USER, ADMIN  -> enum열거형 타입
    return (
        <Routes>
            <Route path="/home" exact={true} element={<HomePage />}/>
            <Route path="/login" exact={true} element={<LoginPage />}/>
            <Route path="/joinForm" exact={true} element={<JoinPage />}/>
            <Route path="/tent" exact={true} element={<TentPage />}/>
            <Route path="/schedule" exact={true} element={
                <PrivateRouter role={role} allowedRoles={["ADMIN", "USER"]}>
                    <SchedulePage /> 
                </PrivateRouter>
            }/>
            <Route path="/notice" exact={true} element={
                <PrivateRouter role={role} allowedRoles={["USER"]}>
                    <NoticePage /> 
                </PrivateRouter>
            }/>
        </Routes>        
    )
}

export default AppRouter