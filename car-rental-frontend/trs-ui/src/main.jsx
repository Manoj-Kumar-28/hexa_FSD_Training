
import { createRoot } from 'react-dom/client'

import App from './App.jsx'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import BuyerDashboard from './buyer/buyer-dashboard.jsx'
import AdminBuyerBookings from './admin/buyer-bookings.jsx'
import AdminOwnerCars from './admin/owner-cars.jsx'
import AdminBookings from './admin/admin-bookings.jsx'
import AdminStatusBookings from './admin/admin-status-bookings.jsx'
import OwnerDashboard from './owner/owner-dashboard.jsx'
import AdminDashboard from './admin/admin-dashboard.jsx'
import BuyerSignUp from './buyer/sign-up.jsx'
import BrowseCars from './buyer/browse-cars.jsx'
import CarDetails from './buyer/car-details.jsx'
import BuyerBooking from './buyer/booking.jsx'
import MyBuyerBookings from './buyer/my-bookings.jsx'
import BuyerProfile from './buyer/profile.jsx'
import Login from './components/login.jsx'
import CarList from './owner/car-list.jsx'
import Sample from './owner/sample.jsx'

import AddCar from './owner/add-car.jsx'
import EditCar from './owner/edit-car.jsx'
import MyCars from './owner/my-cars.jsx'
import Bookings from './owner/my-bookings.jsx'
import OwnerProfile from './owner/profile.jsx'
import AdminProfile from './admin/profile.jsx'
import MyBookings from './buyer/my-bookings.jsx'
import OwnerSignUp from './owner/owner-signup.jsx'
import Approvals from './admin/approvals.jsx'
import { Provider } from 'react-redux'
import { store } from './store.js'
import BuyerList from './admin/buyer-list.jsx'
import OwnerList from './admin/owner-list.jsx'
import CarsList from './admin/cars-list.jsx'


const routes = createBrowserRouter([

  {
    path: "",
    element: <App />
  },
  {
    path: "/login",
    element: <Login />
  },
  {
    path: "/owner-signup",
    element: <OwnerSignUp />
  },
  {
    path: "/buyer/sign-up",
    element: <BuyerSignUp />
  },
  {
    path: "/buyer-dashboard",
    element: <BuyerDashboard />
  },
  {
    path: "/buyer/profile",
    element: <BuyerProfile />
  },
  {
    path: "/browse",
    element: <BrowseCars />
  },
  {
    path: "/car-details/:id",
    element: <CarDetails />
  },
  {
    path: "/booking/:id",
    element: <BuyerBooking />
  },
  {
    path: "/myBookings",
    element: <MyBookings />
  },
  {
    path: "/owner/add-car",
    element: <AddCar />
  },
  {
    path: "/owner/edit-car/:id",
    element: <EditCar />
  },
  {
    path: "/owner/my-cars",
    element: <MyCars />
  },
  {
    path: "/owner/my-bookings",
    element: <Bookings />
  },
  {
    path: "/owner/profile",
    element: <OwnerProfile />
  },
  {
    path: "/owner-dashboard",
    element: <OwnerDashboard />,
    children: [
      {
        index: true,
        element: <Sample />
      },
      {
        path: "car-list/:carStatus",
        element: <CarList />
      },

    ]
  },
  {
    path: "/admin/profile",
    element: <AdminProfile />
  },
  {
    path: "/admin/buyers",
    element: <BuyerList />,
    children: [
      {
        path: ":buyerId/bookings",
        element: <AdminBuyerBookings />
      }
    ]
  },
  {
    path: "/admin/owners",
    element: <OwnerList />,
    children: [
      {
        path: ":ownerId/cars",
        element: <AdminOwnerCars />
      }
    ]
  },
  {
    path: "/admin/cars",
    element: <CarsList />
  },
  {
    path: "/admin-bookings",
    element: <AdminBookings />
  },
  {
    path: "/admin-approvals",
    element: <Approvals />
  },
  {
    path: "/admin-dashboard",
    element: <AdminDashboard />,
    children: [
      {
        path: "bookings/:status",
        element: <AdminStatusBookings />
      }
    ]
  }
])




createRoot(document.getElementById('root')).render(

    <Provider store={store}>
      <RouterProvider router={routes}>
        <App />
      </RouterProvider>
    </Provider >
 
)
