import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "sort"
})
export class ReportSortPipe  implements PipeTransform {
  transform(array: Array<any>, field: string,sortType:boolean): Array<any>{
      console.log("in sorting");
      console.log(array);
      console.log(array.length);
      let ffff = array[0];
      console.log(ffff);
    array.sort((a: any, b: any) => {
        if(typeof a[field] === 'number'||typeof a[field] === 'string'){
               if (a[field] < b[field]) {
                    return (sortType==true)?-1:1;
                } else if (a[field] > b[field]) {
                    return(sortType==true)? 1:-1;
                } else {
                    return 0;
                }
        }else if(b[field] instanceof Date){
            return this.compareReportDate(a[field],b[field],sortType);
        }
   
    });
    return array;
  }

    compareReportDate(source,destination,sortType){
    let a=new Date(source);
    let d=new Date(destination);
    if(d > a){
      return (sortType==true)?1:-1;
    }else if(a< d){
      return (sortType==true)?-1:1;
    }else{
      return 0;
    }
    
  }
}