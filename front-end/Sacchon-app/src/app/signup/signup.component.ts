import { SignupService } from './../services/signup.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  response: any;
  signupForm: any;

  constructor(private fb: FormBuilder, private service: SignupService) {}

  ngOnInit(): void {
    this.signupForm = this.fb.group({
      firstName: [],
      lastName: [],
      email:[],
      address:[],
      phoneNumber:[],
      birthday:[],
      gender:[],
      height:[],
      weight:[],
      bloodType: [],
      diabetesType: [],
      mrn: [],
      password: [],
    })
  }

  signUp(){
    const data = {
      id: 0,
      firstName: this.signupForm.get('firstName').value,
      lastName: this.signupForm.get('lastName').value,
      email: this.signupForm.get('email').value,
      address: this.signupForm.get('address').value,
      phoneNumber: this.signupForm.get('phoneNumber').value,
      dateOfBirth: new Date(this.signupForm.get('birthday').value),
      gender: this.signupForm.get('gender').value,
      height: this.signupForm.get('height').value,
      weight: this.signupForm.get('weight').value,
      bloodType: this.signupForm.get('bloodType').value,
      diabetesType: this.signupForm.get('diabetesType').value,
      medicalRecordNumber: this.signupForm.get('mrn').value,
      password: this.signupForm.get('password').value
    };

    this.service.signUp(data, "http://localhost:9000/signup/patient").subscribe({
      next: res => {
        this.response = res;
      }
    })
  }

}
