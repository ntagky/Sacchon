import { DataInspectorComponent } from './pages/data-inspector/data-inspector.component';
import { DeleteButtonComponent } from './delete-button/delete-button.component';
import { ConsultationsComponent } from './pages/consultations/consultations.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MeasurementsPageComponent } from './pages/measurements-page/measurements-page.component';
import { SignupComponent } from './signup/signup.component';
import { InfoComponent } from './info/info.component';

const routes: Routes = [
  {path: 'delete', component: DeleteButtonComponent},
  { path: 'home', component: HomepageComponent },
  { path: 'consultations', component: ConsultationsComponent },
  { path: 'measurements', component: MeasurementsPageComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'inspector', component: DataInspectorComponent },
  {path: 'info', component: InfoComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
