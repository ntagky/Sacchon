import { ConsultationsComponent } from './pages/consultations/consultations.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { MeasurementsComponent } from './measurements/measurements.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'home', component: HomepageComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'consultations', component: ConsultationsComponent },
  { path: 'person-data', component: MeasurementsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
