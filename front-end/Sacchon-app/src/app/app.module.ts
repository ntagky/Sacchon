import { DeleteButtonComponent } from './delete-button/delete-button.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgxPrintElementModule } from 'ngx-print-element';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SidenavComponent } from './sidenav/sidenav.component';
import { MainContentComponent } from './main-content/main-content.component';
import { ModalMeasurementsComponent } from './modal-measurements/modal-measurements.component';
import { MeasurementsComponent } from './pages/measurements/measurements.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { ConsultationsComponent } from './pages/consultations/consultations.component';
import { ConsultationsTableComponent } from './consultations-table/consultations-table.component';
import { SignupComponent } from './signup/signup.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxSkeletonLoaderModule } from 'ngx-skeleton-loader';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MdbModalModule } from 'mdb-angular-ui-kit/modal';
import { DataInspectorComponent } from './pages/data-inspector/data-inspector.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SidenavComponent,
    MainContentComponent,
    HomepageComponent,
    ConsultationsComponent,
    ConsultationsTableComponent,
    SignupComponent,
    MeasurementsComponent,
    ModalMeasurementsComponent,
    DataInspectorComponent,
    DeleteButtonComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxPrintElementModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgxSkeletonLoaderModule,
    BrowserAnimationsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MdbModalModule,
    NgxChartsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
