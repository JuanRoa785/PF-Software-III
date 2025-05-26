import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { BuscarLibroComponent } from './pages/buscar-libro/buscar-libro.component';
import { LoginComponent } from './pages/login/login.component';
import { LoginFormComponent } from './pages/login/login-form/login-form.component';
import { SignUpFormComponent } from './pages/login/sign-up-form/sign-up-form.component';
import { MenuClienteComponent } from './components/menu-cliente/menu-cliente.component';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { CarritoComponent } from './pages/carrito/carrito.component';
import { HistorialComprasComponent } from './pages/historial-compras/historial-compras.component';
import { MenuAdminComponent } from './components/menu-admin/menu-admin.component';
import { InventarioComponent } from './pages/inventario/inventario.component';
import { CrudLibroComponent } from './pages/crud-libro/crud-libro.component';
import { ReporteComponent } from './pages/reporte/reporte.component';
import { CheckoutComponent } from './pages/checkout/checkout.component';

export const routes: Routes = [
    {
        path: 'buscarLibro',
        component: BuscarLibroComponent,
    },
    {
        path: 'login',
        component: LoginComponent,
        children: [
          { path: 'iniciarSesion', component: LoginFormComponent },
          { path: 'registrarse', component: SignUpFormComponent },
          { path: '', redirectTo: 'iniciarSesion', pathMatch: 'full' } 
        ]
    },
    {
        path: 'cliente',
        component: MenuClienteComponent,
        children: [
          { path: 'perfil', component: PerfilComponent },
          { path: 'carrito', component: CarritoComponent },
          { path: 'historialCompras', component: HistorialComprasComponent },
          { path: '', redirectTo: 'perfil', pathMatch: 'full' } 
        ]    
    },
    {
        path: 'admin',
        component: MenuAdminComponent,
        children: [
          { path: 'inventario', component: InventarioComponent },
          { path: 'crudLibro', component: CrudLibroComponent },
          { path: 'reporte', component: ReporteComponent },
          { path: '', redirectTo: 'inventario', pathMatch: 'full' } 
        ]    
    },
    {
        path: 'checkout',
        component: CheckoutComponent
    },
    {
        path: '**',
        component: HomeComponent,
    },
    {
        path: '',
        component: HomeComponent,
    }
];
