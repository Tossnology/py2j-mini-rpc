

def hello(params):
    # 自行转换params
    res = int(params[0])

    # 自定义服务
    res = res + 1

    # 返回结果字符串
    return str('hello, ' + str(res))