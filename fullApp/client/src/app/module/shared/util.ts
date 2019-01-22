import { SessionService } from "./shared/SessionService";

export class Util {
  
  constructor(private session:SessionService){

  }
  static convertDate(str) {
    if (!str) {
      return null;
    }
    return new Date(str*1000);
  }
  static convertDateToMili(str){
    //1539109800
    if(str.length==10){
      return str;
    }else{
      let dd=""+new Date(str).getTime();
      if(dd.length==10){
        return dd;
      }else{
        return new Date(str).getTime()/1000;
      }
    }
    
  }
  //2018-05-21T18:30:00.000Z
  static convertUTFDateWithHighen(str) {
    if (!str) {
      return null;
    }
    let temp = str.split("-");
    let d = new Date();
    if (temp && temp.length == 3) {
      d.setMonth(temp[1]);
      d.setDate(temp[2].split("T")[0]);
      d.setFullYear(temp[0]);

    }
    var now_utc = new Date(d.getUTCFullYear(), d.getUTCMonth() - 1, d.getUTCDate(), d.getUTCHours(), d.getUTCMinutes(), d.getUTCSeconds());

    return now_utc;
  }
  //2018-04-30T18:30:00.000Z
  static convertUTFDate(str) {
    try {


      if (!str) {
        return null;
      }
      let temp = str.split("/");
      let d = new Date();
      if (temp && temp.length == 3) {
        d.setMonth(temp[0]);
        d.setDate(temp[1]);
        d.setFullYear(temp[2]);

      } else {
        return this.convertUTFDateWithHighen(str);
      }

      var now_utc = new Date(d.getUTCFullYear(), d.getUTCMonth() - 1, d.getUTCDate(), d.getUTCHours(), d.getUTCMinutes(), d.getUTCSeconds());

      return now_utc;
    } catch (e) {
      return this.convertUTFDateWithHighen(str);
    }
  }

  static convertDateToString(date) {
    //04/19/2018
    let d = new Date();
    let temp = date.split("/");
    d.setDate(temp[1]);
    d.setMonth(temp[0]);
    d.setFullYear(temp[2]);
    var now_utc = new Date(d.getUTCFullYear(), d.getUTCMonth() - 1, d.getUTCDate(), d.getUTCHours(), d.getUTCMinutes(), d.getUTCSeconds());

    return now_utc;
  }

  static S4() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
  }

  // then to call it, plus stitch in '4' in the third group
  static getUUID() {
    return (this.S4() + this.S4() + "-" + this.S4() + "-4" + this.S4().substr(0, 3) + "-" + this.S4() + "-" + this.S4() + this.S4() + this.S4()).toLowerCase();

  }


  static ConvertTimeFormat(d) {
    return `${d.getHours()}:${d.getMinutes()} ${d.getUTCDate()}/${d.getMonth() + 1}`
  }

  static displayDateFormat(d) {
    
    if(localStorage.getItem("setting") && localStorage.getItem("setting")!=="null"){
      let obj=JSON.parse( localStorage.getItem("setting"));
      if(obj.dateFormat){
        let dformat =obj.dateFormat;
         let split=dformat.split("/");
         if(split.length==3){
          return Util.getDateSpl(split[0],d)+"/"+Util.getDateSpl(split[1],d)+"/"+Util.getDateSpl(split[2],d);
         }else{
           split=dformat.split("-");
            if(split.length==3){
              return Util.getDateSpl(split[0],d)+"-"+Util.getDateSpl(split[1],d)+"-"+Util.getDateSpl(split[2],d);
            }
         }
      }else{
          let day = Util.getDateValue(d.getDay());
        let month = Util.getDateValue(d.getMonth() + 1);
        return `${day}/${month}/${d.getFullYear()}`;
      }
    }else{

      let day = Util.getDateValue(d.getDay());
      let month = Util.getDateValue(d.getMonth() + 1);
      return `${day}/${month}/${d.getFullYear()}`;
    }
  }
 static getDateSpl(spl,date){
    if(spl=="MM"||spl=="mm"){
      return  Util.getDateValue(parseInt(date.getMonth()) + 1);
    }else if(spl=="dd"||spl==="DD"){
      return  Util.getDateValue(parseInt(date.getUTCDate())+1);
    }else if(spl=="yyyy"||spl==="YYYY"){
      return date.getFullYear();
    }
 }
  static getDateValue(val) {
    if (val >= 1 && val <= 9) {
      return "0" + val;
    } else {
      return val;
    }
  }

    static getFinancialYear(session) {
      let d=new Date();
    if(session.currentSetting.leaveStartEndMonths){
      if(session.currentSetting.leaveStartEndMonths=="Jan-Dec"){
        return d.getFullYear()+"-"+""+(d.getFullYear()+1);
      }else{
        if(d.getMonth()<=2){
          return (d.getFullYear()-1)+"-"+d.getFullYear();
        }else{
          return d.getFullYear()+"-"+""+(d.getFullYear()+1);
        }
      }
    }
  }

}

