import { useState } from 'react'

import './App.css'
import GetUsers from './components/get-users'
import { Link } from 'react-router-dom'

function App() {

  return (
    <div>
      <h4>Welcome to User Management Dashboard</h4>
      <Link to="/get-users">Get Users</Link>
      <br />
      <Link to="/add-users">Add Users</Link>
    </div>
  )
}

export default App
