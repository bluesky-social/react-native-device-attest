package main

import (
	"net/http"
	"os"

	"github.com/labstack/echo/v4"
	"github.com/urfave/cli/v2"
)

func main() {
	app := cli.App{
		Name: "react-native-device-attest test server",
		Flags: []cli.Flag{
			&cli.StringFlag{
				Name:  "addr",
				Value: ":8080",
			},
		},
		Action: run,
	}

	app.Run(os.Args)
}

type Server struct {
	httpd *http.Server
	echo  *echo.Echo
}

var run = func(cmd cli.Context) error {
	httpd := &http.Server{
		Addr: cmd.String("addr"),
	}

	e := echo.New()

	s := Server{
		httpd: httpd,
		echo:  e,
	}

	s.serve()

	return nil
}

func (s *Server) serve() {
	s.echo.POST("/validate", s.handleValidate)
}

func (s *Server) handleValidate(e echo.Context) error {
	return nil
}
