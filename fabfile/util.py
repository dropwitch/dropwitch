def camel_case(snake_case):
    words = snake_case.split('_')
    first = words[0]
    return first + ''.join((w.capitalize() for w in words[1:]))


def pascal_case(snake_case):
    words = snake_case.split('_')
    return ''.join((w.capitalize() for w in words))
