import { LoginComponent } from './pages/login/login.component';
import { DataInspectorComponent } from './pages/data-inspector/data-inspector.component';
import { DeleteButtonComponent } from './delete-button/delete-button.component';
import { ConsultationsComponent } from './pages/consultations/consultations.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { MeasurementsComponent } from './pages/measurements/measurements.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InfoComponent } from './info/info.component';

const routes: Routes = [
  { path: 'delete', component: DeleteButtonComponent},
  { path: 'home', component: HomepageComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'consultations', component: ConsultationsComponent },
  { path: 'person-data', component: MeasurementsComponent },
  { path: 'inspector', component: DataInspectorComponent },
  { path: 'info', component: InfoComponent },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
