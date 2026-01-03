import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/service/security/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  messageAr: string = "";
  messageEn: string = "";


  constructor(private fb: FormBuilder, private authService: AuthService , private router:Router) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onLogin() {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    // console.log('Login payload:', this.loginForm.value);

    this.authService.login(this.loginForm.value).subscribe({
    next: response =>{
        // got to page products if signup success
        sessionStorage.setItem("token", response.token);
        // sessionStorage.setItem("role", response.userRoles);
        this.router.navigateByUrl('/mainpage');
      },
    error: errorRespo =>{
        console.log( errorRespo);
        this.messageAr = errorRespo.error?.messageAr || 'حدث خطأ غير متوقع';
        this.messageEn = errorRespo.error?.messageEn || 'Unexpected error';
        setTimeout(() => {
          this.messageAr = "";
          this.messageEn = "";
        }, 3000);
      }
  });

    // this.authService.login(this.loginForm.value).subscribe(...)
  }

}
