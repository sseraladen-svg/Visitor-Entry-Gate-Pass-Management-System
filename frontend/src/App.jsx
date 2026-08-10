import { Navigate, Route, Routes } from 'react-router-dom';
import Layout from './components/Layout.jsx';
import ProtectedRoute from './components/ProtectedRoute.jsx';
import Dashboard from './pages/Dashboard.jsx';
import GatePasses from './pages/GatePasses.jsx';
import Login from './pages/Login.jsx';
import NewGatePass from './pages/NewGatePass.jsx';
import Verify from './pages/Verify.jsx';
import Visitors from './pages/Visitors.jsx';

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route
        element={
          <ProtectedRoute>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route path="/" element={<Dashboard />} />
        <Route path="/visitors" element={<Visitors />} />
        <Route path="/gate-passes" element={<GatePasses />} />
        <Route path="/gate-passes/new" element={<NewGatePass />} />
        <Route path="/verify" element={<Verify />} />
      </Route>
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
