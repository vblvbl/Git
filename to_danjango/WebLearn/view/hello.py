# coding=utf-8
from django.http.response import HttpResponse
from django.http.response import HttpResponseRedirect
from django.shortcuts import render_to_response


def sayhi(requset):
    return HttpResponse("<h1>你好啊，这是我的第一个自定义page</h1>")


def mytiem(request):
    import datetime
    now = datetime.datetime.now()
    html = '<html><bldy>this time is :%s</body><html>' % now
    return HttpResponse(html)


def myplustime(request, index):
    index = int(index)
    import datetime
    time = datetime.datetime.now() + datetime.timedelta(hours=index)
    html = '<html><bldy>In %s hours will is :%s</body><html>' % (index, time)
    return HttpResponse(html)


def myhtmlshow(resquest):
    from django.shortcuts import render_to_response
    import os
    listfile = os.popen('ls').read()
    return render_to_response('myview.html')


def show_table(request):
    from django.shortcuts import render_to_response
    table = {'liakng': [22, 'male', 173], 'wangwu': [21, 'famale', 172]}
    return render_to_response('demoshow.html', {'tb': table})


def show_requestDetail(request):
    from django.shortcuts import render_to_response
    need_map = dict()
    for k, v in request.META.items():
        if v == '' or k == 'LS_COLORS':
            continue
        else:
            need_map[k] = v

    return render_to_response('res_detail.html', {'res_map': need_map})


def show_table(request):
    from django.shortcuts import render_to_response
    return render_to_response('show_table.html')


def show_table_detail(request):
    from django.shortcuts import render_to_response
    error = False
    from dbweb.models import Publisher
    if 'q' in request.GET and request.GET['q']:
        q = request.GET['q']
        pub = Publisher.objects.filter(name__icontains=q)
        return render_to_response('show_table_detail.html', {'pub': pub})
    else:
        error = True
        return render_to_response('show_table.html', {'error': error})
        # if 'q' in request.GET:
        #     message = request.GET['q']
        # else:
        #     message = 'it is empty'


def do_form(request):
    from django.shortcuts import render_to_response
    return render_to_response('content.html')


def show_all_tab(request):
    from django.shortcuts import render_to_response
    error = []
    if request.method == 'POST':
        if not request.POST.get('subject', ''):
            error.append('subject is error!')
        if not request.POST.get('message', ''):
            error.append('message is error!')
        if not request.POST.get('email', ''):
            error.append('email is error!')

    subject = request.POST['subject']
    message = request.POST['message']
    email = request.POST.get('email', 'noreply@example.com')
    if not error:
        from django.core.mail import send_mail
        send_mail(subject,
                  message, email,
                  ['122726894@qq.com'], )
        return render_to_response('tab_ok.html')
    from django.template import RequestContext
    return render_to_response('content.html',
                              {'error': error, 'subject': subject, 'message': message, 'email': email},
                              context_instance=RequestContext(request))


# def build_form(request):
#     from django.shortcuts import render_to_response
#     return render_to_response('myform.html')


def method_form(request):
    from WebLearn.view.form import MyForm
    if request.method == 'GET':
        fm = MyForm(request.GET)
        if fm.is_valid():
            from django.core.mail import send_mail
            cd = fm.cleaned_data
            send_mail(cd['subject'],
                      cd['message'], cd['email'],
                      ['122726894@qq.com'], )
            return HttpResponseRedirect('/cont/thanks/')
    else:
        fm = MyForm()
    return render_to_response('myform.html',{'fm' :fm})
