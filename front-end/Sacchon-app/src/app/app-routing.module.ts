import { AiDiagnosisComponent } from './pages/ai-diagnosis/ai-diagnosis.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { LocalStorageService } from './services/local-storage.service';
import { LoginComponent } from './pages/login/login.component';
import { InspectorComponent } from './pages/inspector/inspector.component';
import { GoodByeComponent } from './pages/goodbye/goodbye.component';
import { ConsultationsComponent } from './pages/consultations/consultations.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MeasurementsComponent } from './pages/measurements/measurements.component';
import { SignupComponent } from './signup/signup.component';
import { InfoComponent } from './info/info.component';
import { Router } from  '@angular/router';
import { Location } from '@angular/common';

const routes: Routes = [
  { path: 'delete', component: GoodByeComponent },
  { path: 'home', component: HomepageComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'consultations', component: ConsultationsComponent },
  { path: 'inspector', component: InspectorComponent },
  { path: 'info', component: InfoComponent },
  { path: 'login', component: LoginComponent },
  { path: 'measurements', component: MeasurementsComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'inspector', component: InspectorComponent },
  { path: 'info', component: InfoComponent},
  { path: 'signout', component: LoginComponent},
  { path: 'ai-diagnosis', component: AiDiagnosisComponent},
  { path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

  constructor(
    localStoreService: LocalStorageService,
    private router: Router,
    private location: Location
    ) {
    // Redirect user to login page if there is none stored user-id
    let nonAuthorizedEndPoints = ["/login", "/signup", "/ai-diagnosis"];
    if (Number(localStoreService.getData("user")) <= 0 || localStoreService.getData("user") == null) {
      if (!nonAuthorizedEndPoints.includes(this.location.path()))
        this.router.navigate(['/login', {unauthorized: JSON.stringify(true)}]);
    }
  }
}
