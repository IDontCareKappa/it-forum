import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/_services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {

  form: any = {
    username: null,
    email: null,
    password: null
  };

  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';


  

  constructor(private authService: AuthService, private _router: Router) { }

  ngOnInit(): void { }

  onSubmit(): void {

    const { username, email, password } = this.form;
    this.authService.register(username, password, email).subscribe(
      data => {
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        this._router.navigate(['home'])
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );

  }

}