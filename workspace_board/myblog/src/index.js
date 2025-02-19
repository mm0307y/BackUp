import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

const root = ReactDOM.createRoot(document.querySelector('#root'));
root.render(
  <div className='text-4xl m-2 p-3 bg-green-600'>
    <App />
  </div>
);

