while : ; do
  printf "github id > "
  read githubId

  if [[ -z "$githubId" ]]; then
    printf '%s\n' "github id is required!"
    continue
  else
    break
  fi
done

curl \
-u $githubId \--request POST \
--data '{"event_type": "deploy"}' \
https://api.github.com/repos/depromeet/miracle-backend/dispatches

printf "github action!"
