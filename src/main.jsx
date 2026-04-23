
import { createRoot } from 'react-dom/client'

import App from './App.jsx'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import GetUsers from './components/get-users.jsx'
import AddUser from './components/add-users.jsx'


const routes = createBrowserRouter([
  {
    path: "",
    element: <App />
  },
  {
    path: "get-users",
    element: <GetUsers />
  },
  {
    path: "add-users",
    element: <AddUser />
  }
])


createRoot(document.getElementById('root')).render(
  <RouterProvider router={routes}>
    <App />
  </RouterProvider>
)
