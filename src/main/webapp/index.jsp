<!DOCTYPE html>
<html lang="it">
   <head>
      <title>Unimart</title>

      <%@include file="WEB-INF/results/general.jsp"%>
      <link href="css/homepage.css" type="text/css" rel="stylesheet">
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/navbar/navbar.js" defer></script>
   </head>
   <body>
      <header>
         <form autocomplete="off">
            <input type="text" id="searchBar" placeholder="Cerca per codice IAN, nome prodotto o categoria" required>
         </form>
         <div id="resultsSearch">

         </div>

         <a href="LoginManager" id="login">
            <c:choose>
               <c:when test="${utente != null}">
                  <img src="${pageContext.request.contextPath}/file/${utente.fotoProfilo}" height="48px" width="48px" style="border-radius: 30px">
               </c:when>
               <c:otherwise>
                  <img src="icons/account_circle_white.svg">
               </c:otherwise>
            </c:choose>

         </a>

      </header>
      <section>
         <div id="feedback"><a href="CouponManager/list">Feedback</a></div>
         <div>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed lacinia nibh vel lorem sollicitudin elementum. In tempor a augue ut sagittis. Nulla nec ullamcorper orci. Phasellus commodo erat et metus lacinia, quis cursus lectus mollis. Curabitur suscipit tellus quis tellus efficitur, sed fringilla tortor efficitur. Proin eu nisl eu ante lacinia blandit. Pellentesque at enim lacus. Nunc viverra, eros nec rhoncus suscipit, erat justo porta mauris, ac pulvinar dolor metus nec neque. Phasellus a leo porttitor, sollicitudin orci nec, condimentum urna. Nullam non erat sed libero molestie pharetra vitae a metus. Pellentesque non tristique metus. Phasellus sollicitudin augue faucibus eleifend tristique.
            Etiam varius turpis auctor, vestibulum ligula vel, dignissim purus. Sed nec consequat ex, vel tempor ante. Nullam consequat nisl in magna elementum, sed consectetur diam scelerisque. Nunc nunc tellus, congue vel sagittis ut, sodales sed felis. Cras ac placerat est. Duis aliquam dictum viverra. Etiam sapien diam, pretium nec finibus ac, blandit eget purus. Etiam pretium accumsan ultrices. Pellentesque vel libero eu est tempus finibus. Fusce bibendum risus neque, a facilisis lectus faucibus eu. Curabitur fringilla eu lectus eu malesuada. Nulla elementum magna tortor, tristique consequat ex bibendum id. Cras tincidunt, ligula at efficitur luctus, mauris elit auctor eros, ut laoreet risus tortor ac felis. Suspendisse elit tellus, mattis et libero a, dignissim fermentum nunc. Maecenas sit amet ipsum ut purus faucibus facilisis.
            Quisque suscipit ligula in est facilisis cursus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Duis euismod aliquet orci, in tincidunt libero lobortis vitae. Quisque est erat, ultricies at elit sed, sagittis tristique est. Donec vestibulum ligula eget erat imperdiet varius. Etiam ultricies consectetur metus ac maximus. Pellentesque eleifend, purus eget laoreet semper, mi dolor sodales lectus, ut sagittis est magna sed leo. Etiam molestie vel est pharetra eleifend. Suspendisse pretium neque non quam vehicula, non interdum arcu condimentum. Ut tincidunt condimentum semper. Praesent varius sapien at feugiat posuere.
            Aliquam at diam lacinia, maximus ipsum a, facilisis leo. Ut rhoncus venenatis nunc, nec scelerisque libero dapibus nec. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Quisque erat sem, volutpat quis iaculis at, tempor nec risus. Nullam odio nisl, feugiat at consectetur et, posuere porttitor lacus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam eu eleifend odio. Proin hendrerit porttitor nibh, vel fermentum nibh vehicula at. Aliquam vitae ligula ac diam sagittis maximus. Vestibulum laoreet mi sed commodo mattis. Sed id sem sed turpis tincidunt efficitur. Vestibulum tincidunt pulvinar risus, ac finibus eros vestibulum ut. Quisque a ligula eu nulla aliquam feugiat id sit amet elit. Aliquam erat volutpat.
            Quisque ultrices metus consectetur justo bibendum hendrerit. Donec iaculis velit tortor, a tempus sapien gravida quis. Proin ultrices mattis velit, vitae posuere lectus volutpat non. Vivamus neque nulla, venenatis vitae velit sed, dignissim elementum massa. Curabitur a felis eget velit feugiat viverra vel ut quam. Nulla ultrices iaculis massa, ac hendrerit nulla convallis sed. Aenean non velit cursus, egestas turpis quis, sagittis tortor. Donec lectus ipsum, scelerisque sit amet lorem in, pharetra euismod tellus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce quis molestie mauris, vel elementum ex. Pellentesque vestibulum est at sapien maximus, sit amet malesuada libero posuere.
            Ut ultrices consectetur quam vitae scelerisque. Donec tincidunt imperdiet vehicula. Nunc porttitor blandit dolor, vitae viverra libero semper ac. Maecenas faucibus orci mollis, luctus nibh id, pharetra mi. Etiam ac tellus sapien. Aliquam elementum tempor tellus, ut imperdiet felis finibus tristique. Vivamus condimentum lectus at nisi ullamcorper euismod. Ut interdum neque felis, non blandit arcu iaculis nec. Curabitur lobortis quis diam non sodales.
            Nam aliquam hendrerit mauris vitae fermentum. Sed vitae lectus est. Praesent lacinia efficitur blandit. Donec vitae semper nulla, a efficitur metus. Praesent eu mauris fermentum, pellentesque lacus eu, pellentesque lacus. Phasellus semper risus quis felis malesuada viverra. Praesent eget sem nulla. Aenean lorem leo, suscipit sed tincidunt id, mollis vel orci. Maecenas leo nunc, faucibus at mauris vitae, maximus tristique turpis.
            Sed elementum magna feugiat nibh venenatis tincidunt. Aenean sollicitudin convallis nisl eu tincidunt. Praesent ullamcorper eros non ex rhoncus vehicula. Nam sit amet diam leo. Sed neque orci, porttitor id eros id, ultricies pellentesque libero. Suspendisse ac sem porttitor, volutpat massa sed, tristique purus. Morbi eu nunc nulla. Sed dignissim lacinia cursus. Curabitur condimentum lobortis metus, at volutpat lacus porta mollis. Pellentesque sollicitudin dui a nibh porttitor blandit. Proin vel bibendum erat. Proin sed nibh risus.
            Etiam blandit, orci ut vulputate viverra, sapien nisl finibus libero, non ultricies dolor sem quis nisi. Nulla vel dolor nec turpis pulvinar bibendum non eu urna. Mauris vel sapien vel mi malesuada iaculis. Ut varius velit felis, quis scelerisque nisl aliquam et. Nulla a nunc elit. Quisque sit amet ante pretium, pellentesque augue vitae, auctor diam. Aliquam tincidunt malesuada sapien, id luctus turpis ultricies tempus. Nam lacinia nulla a mi accumsan condimentum. Suspendisse elit erat, mattis eu porta eu, mattis non metus. Pellentesque ac massa nec sapien accumsan iaculis. Praesent felis turpis, imperdiet non pellentesque vel, imperdiet id justo. In hac habitasse platea dictumst.
            Vestibulum sodales luctus eros id sagittis. Praesent tincidunt, mauris et faucibus vestibulum, purus purus tincidunt arcu, sit amet facilisis odio lorem hendrerit risus. Vestibulum sagittis neque mauris, ac facilisis massa elementum quis. Sed id volutpat velit. Nullam laoreet, neque vel condimentum elementum, enim metus congue erat, eu malesuada libero purus sed lectus. Cras eu diam sit amet sem hendrerit facilisis. Etiam quis feugiat sem. Phasellus quis libero quis ante aliquet rutrum vel eu magna. Nunc diam mauris, placerat sit amet sem a, luctus vestibulum eros. Suspendisse pharetra, odio et pulvinar bibendum, erat magna scelerisque turpis, at malesuada lorem dui at risus. Donec in justo massa. Donec a vehicula sem.
            Sed nec ullamcorper lorem. In hac habitasse platea dictumst. Vestibulum consequat id leo sit amet euismod. Ut et maximus nibh. Proin aliquam feugiat congue. Sed ante est, auctor a varius et, elementum ac tellus. Mauris ultricies ultrices velit et mollis. Phasellus ut volutpat orci. Aliquam enim velit, feugiat non posuere malesuada, pellentesque vitae dui.
         </div>
      </section>
   </body>
</html>