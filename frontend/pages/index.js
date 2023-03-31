import * as React from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import ProTip from "../src/ProTip";
import Link from "../src/Link";
import Copyright from "../src/Copyright";

export default function Index() {
  return (
    <Container maxWidth="sm">
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Material UI - Next.js example
        </Typography>
        <Link href="/about" color="secondary">
          Go to the Sign In page
        </Link>
        <br />
        <Link href="/SignUp" color="secondary">
          Go to the Sign Up page
        </Link>
        <br />
        <Link href="/Dashboard" color="secondary">
          Go to the Dashboard
        </Link>
        <ProTip />
        <Copyright />
      </Box>
    </Container>
  );
}
