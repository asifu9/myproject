import { Component, OnInit ,Input} from '@angular/core';
import { FormGroup, FormControl } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { Company } from "../../../shared/shared/models";
import { SessionService } from "../../../shared/shared/SessionService";
import { HttpService } from "../../../shared/shared/httpService";


@Component({
  selector: 'app-company-create',
  templateUrl: './company-create.component.html',
  styleUrls: ['./company-create.component.scss']
})
export class CompanyCreateComponent implements OnInit {

 

  @Input()
  company:Company;
  status:string='';
  buttonValue:string='';
  companyForm: FormGroup;
  header:string='';

  constructor(private rp: ActivatedRoute,private route:Router, 
  private sessionService: SessionService,private httpService:HttpService) { 
        rp.params.subscribe(p =>{ 
      if(p && p.id){
          this.httpService.getData("/Company/"+p.id).subscribe(
          res=>{
            this.company=res;
                this.companyForm = new FormGroup({
                name: new FormControl(res.name),
                uname:new FormControl(res.uname),
                active:new FormControl(res.active)
              });
            this.buttonValue='Update';
            this.status='edit';
            this.header='Updating company: ' + this.company.name;
          }
        )
      }else{
          
          this.status='new';
          this.buttonValue='Create';
          this.createNewForm();
          this.header='Create New Company'
          this.company=new Company();
      }
    
    });
  }

    createNewForm() {
    this.companyForm = new FormGroup({
      name: new FormControl(),
      uname: new FormControl(),
      active:new FormControl(1)
    });
  }

  ngOnInit() {
  }


  createUpdate() {
    console.log("=======save c;locke");
    console.log('status = ' + this.status)
    console.log(this.company);
    console.log(this.companyForm);
    if (this.status == 'new' && this.company) {
      this.company = new Company();
      this.company = Object.assign(this.company, ...this.companyForm.value);
     this.httpService.postData("/Company/",this.company).subscribe(
       data=>{
         this.route.navigate(['/admin/company'], { });
        }
     )
    } else if (this.status == 'edit' && this.company) {
      //its an update
      let obj = new Company();
      obj.name = this.companyForm.value.name;
      this.company = Object.assign(this.company, ...this.companyForm.value);
      console.log("----edit save----");
      console.log(this.company);
      this.httpService.putData("/Company/",this.company).subscribe(
        data => {
          this.route.navigate(['/admin/company'], { });
        }
      );
    }


    console.log(this.company);
  }

  cancel() {
    this.company = null;
    this.companyForm = null;
    this.route.navigate(['/admin/company'], {});
  }


}
