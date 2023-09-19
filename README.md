Postgresql DB에 아래 함수 생성해야 합니다.

```postgresql
create function set_flag_number(flag int) returns int
    language plpgsql
as
$$
declare
current_id int;
BEGIN

SELECT last_value FROM members_id_seq into current_id;
flag = current_id/10000 as int;
return flag;
END;
$$;

alter function set_flag_number(flag int) owner to blueuser;

```