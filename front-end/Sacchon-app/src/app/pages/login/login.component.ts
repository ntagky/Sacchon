import { LocalStorageService } from './../../services/local-storage.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { LoginService } from './../../services/login.service';
import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

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
    private route: ActivatedRoute,
    private router: Router,
    private localStoreService: LocalStorageService,
    private toastr: ToastrService
  ) {
    this.localStoreService.saveData("user", "0");
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ["", [Validators.required]],
      password: ["", [Validators.required, Validators.min(1)]],
    });

    if (this.route.snapshot.paramMap.get("inactive") != null)
      this.showSuccess();
  }

  showSuccess() {
    this.toastr.error('It seems that you have logged out.', 'Login again', {
      timeOut: 3500,
    });
  }

  login(): void {
    this.service.get("http://localhost:9000/login?email=" + this.loginForm.get('email').value +
     "&password=" + this.loginForm.get('password').value).subscribe({
      next: res => {
        this.loginRespone = res;
        if (this.loginRespone.id > 0) {
          this.localStoreService.saveData("user", this.loginRespone.id + "");
          this.router.navigateByUrl('/')
        } else
          this.invalidData = true;
      }
    });
  }
}

// this.service.get("http://localhost:9000/login?email=" + this.loginForm.get('email').value +
//      "&password=" + this.cryptoService.encrypt(this.loginForm.get('password').value, this.loginForm.get('password').value)).subscribe({
