import { LocalStorageService } from './../../services/local-storage.service';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { LoginService } from './../../services/login.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginRespone: any;
  loginForm: any;
  invalidData: boolean = false;

  constructor(
    private service: LoginService,
    private fb: FormBuilder,
    private router: Router,
    private localStore: LocalStorageService
  ) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ["", [Validators.required]],
      password: ["", [Validators.required, Validators.min(1)]],
    });
  }

  login(): void {
    this.service.get("http://localhost:9000/login?email=" + this.loginForm.get('email').value + "&password=" + this.loginForm.get('password').value).subscribe({
      next: res => {
        this.loginRespone = res;
        if (this.loginRespone.id > 0) {
          this.localStore.saveData("user", this.loginRespone.id + "");
          this.router.navigateByUrl('/')
        } else
          this.invalidData = true;
      }
    });
  }
}
