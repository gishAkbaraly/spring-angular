import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { inject } from "@angular/core";
import { AuthService } from '../services/auth.service';


export const AuthorizationGuard: CanActivateFn = (route, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);
 
  if(!auth.isAuthenticated) {
    router.navigateByUrl('/login')
    return false
  }else{
    let requiredRoles = route.data['roles'];
    let userRoles = auth.roles;
    for(let role of userRoles){
      if(requiredRoles.includes(role)){
        return true;
      }
    }
  }
  return false;
};
