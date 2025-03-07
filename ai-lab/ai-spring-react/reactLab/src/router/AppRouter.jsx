import React from 'react'
import { Route, Routes } from 'react-router'
import HomePage from '../components/pages/HomePage'
import LoginPage from '../components/auth/LoginPage'
import TentPage from '../components/pages/TentPage'
import SchedulePage from '../components/pages/SchedulePage'
import NoticePage from '../components/pages/NoticePage'
import JoinPage from '../components/auth/JoinPage'
import PrivateRouter from './PrivateRouter'
import ErrorPage from '../components/pages/ErrorPage'

const AppRouter = () => {
    const role = localStorage.getItem("role") //USER, ADMIN  -> enum열거형 타입
    return (
        <Routes>
            <Route path="/home" exact={true} element={<HomePage />}/>
            <Route path="/tent" exact={true} element={<TentPage />}/>
            <Route path="/schedule" exact={true} element={
                <PrivateRouter role={role} allowedRoles={["ADMIN"]}>
                    <SchedulePage /> 
                </PrivateRouter>
            }/>
            <Route path="/notice" exact={true} element={
                <PrivateRouter role={role} allowedRoles={["USER"]}>
                    <NoticePage /> 
                </PrivateRouter>
            }/>
            <Route path='/error' exact={true} element={<ErrorPage />}></Route>
        </Routes>        
    )
}

export default AppRouter