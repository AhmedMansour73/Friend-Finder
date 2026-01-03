import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/service/security/auth.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  signupForm!: FormGroup; 

  messageAr: string ='';
  messageEn: string ='';

  constructor(private fb: FormBuilder , private authService: AuthService , private router:Router) {}

  ngOnInit(): void {
    this.signupForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      biography: [''],
      profilePicture: [''],
      accountDTO: this.fb.group({
        email: ['', [Validators.required, Validators.email, Validators.pattern(/^[\w.-]+@gmail\.com$/)]],
        password: ['', [Validators.required, Validators.minLength(6)]]
      })
        });
  }
onSubmit() {
  if (this.signupForm.invalid) {
    console.log('Form is invalid!');
    this.signupForm.markAllAsTouched();

    // function recursive لفحص كل الحقول حتى nested
    const checkControls = (group: any, parentKey = '') => {
      Object.keys(group.controls).forEach(key => {
        const control = group.get(key);
        const fullKey = parentKey ? `${parentKey}.${key}` : key;

        if (control instanceof FormGroup) {
          // لو nested FormGroup → recurse
          checkControls(control, fullKey);
        } else if (control?.invalid) {
          if (control.errors?.['required']) {
            console.log(`${fullKey} is required`);
          }
          if (control.errors?.['email']) {
            console.log(`${fullKey} must be a valid email`);
          }
          if (control.errors?.['pattern']) {
            console.log(`${fullKey} must be a Gmail address (example@gmail.com)`);
          }
          if (control.errors?.['minlength']) {
            console.log(`${fullKey} must be at least ${control.errors['minlength'].requiredLength} characters`);
          }
        }
      });
    };

    checkControls(this.signupForm);

    return; 
  }

  // Form
  // console.log('Form is valid, sending data:', this.signupForm.value);
  this.authService.signup(this.signupForm.value).subscribe({
    next: response =>{
        // got to page products if signup success
        sessionStorage.setItem("token", response.token);
        // sessionStorage.setItem("role", response.userRoles);
        this.router.navigateByUrl('/mainpage');
      },
    error: errorRespo =>{
        // console.log( errorRespo);
        this.messageAr = errorRespo.error?.messageAr || 'حدث خطأ غير متوقع';
        this.messageEn = errorRespo.error?.messageEn || 'Unexpected error';
        setTimeout(() => {
          this.messageAr = "";
          this.messageEn = "";
        }, 3000);
      }
  });
}


  


}


