import { Pipe, PipeTransform } from '@angular/core';


@Pipe({
    name: 'reportfilter',
    pure: false
})
export class ReportFilterPipe implements PipeTransform {
  transform(items: any[], filter: any): any[] {
    if (!items || !filter) {
      return items;
    }
    // filter items array, items which match and return true will be kept, false will be filtered out
    return items.filter((item: any) => this.applyFilter(item, filter));
  }
  
  /**
   * Perform the filtering.
   * 
   * @param {Book} book The book to compare to the filter.
   * @param {Book} filter The filter to apply.
   * @return {boolean} True if book satisfies filters, false if not.
   */
  applyFilter(book: any, filter: any): boolean {
    let result=true;
    for (let field in filter) {
      console.log("filter name " +field);
      if (filter[field]) {
        if (typeof filter[field] === 'string') {
          if (book[field].toLowerCase().indexOf(filter[field].toLowerCase()) === -1) {
            result= false;
            break;
          }
        } else if (typeof filter[field] === 'number') {
          if (book[field] !== filter[field]) {
            result= false;
            break;
          }
        }else{
          if(filter[field] instanceof Date){
           result=  this.checkDate(book[field],filter[field]);
            if(!result){
              break;
            }
          } 
        }
      }
    }
    return result;
  }

  checkDate(source,destination){
    let obj1=destination.getDate()+":"+destination.getMonth()+":"+destination.getFullYear();
    let d=new Date(source);
    let obj2=d.getUTCDate()+":"+d.getUTCMonth()+":"+d.getUTCFullYear();
    console.log(obj1 + " : " + obj2 + " : " + (obj1==obj2));
    if(obj1==obj2){
      return true;
    }
    return false;
    
  }

 
}