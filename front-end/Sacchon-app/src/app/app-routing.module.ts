import { MeasurementsComponent } from './measurements/measurements.component';
import { MainContentComponent } from './main-content/main-content.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  // { path: 'home', component: MainContentComponent },
  // { path: '', redirectTo: '/home', pathMatch: 'full'  }
  { path: 'person-data', component: MeasurementsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
