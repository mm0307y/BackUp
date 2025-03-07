import React from 'react';
import ReactDOM from 'react-dom/client';
import 'bootstrap/dist/css/bootstrap.min.css';
import App from './App'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import LoginPage from './components/auth/LoginPage';
import AuthProvider from './components/auth/AuthProvider';
import JoinPage from './components/auth/JoinPage';


const root = ReactDOM.createRoot(document.querySelector('#root'));
root.render(
  <>
    <BrowserRouter>
    <Routes>
      <Route path='/' element={<LoginPage />}></Route>
      <Route path='/joinForm' element={<JoinPage />}></Route>
      <Route path='/*' element={
        <AuthProvider>
            <App />
        </AuthProvider>
      }></Route>
      </Routes>
    </BrowserRouter>
  </>
);

